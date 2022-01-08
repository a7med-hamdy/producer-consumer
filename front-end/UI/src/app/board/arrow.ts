import Konva from 'konva'
export class Arrow{
  arrow!:Konva.Arrow;
  Source!:Konva.Group;
  Destination!:Konva.Group;
  constructor(src:Konva.Group, dst:Konva.Group){
    this.Source = src;
    this.Destination = dst;
    this.arrow = new Konva.Arrow({
      points:[
      this.Source.x()+33,
      this.Source.y()+33,
      this.Destination.x()-33,
      this.Destination.y()-33
    ],
    fill:'black',
    stroke:'black'

    });
    var component = this;
    function Follow(){
      const pointsArr = [
        component.Source.x()+33,
        component.Source.y()+33,
        component.Destination.x()-33,
        component.Destination.y()-33,
      ]
      component.arrow.setAttrs({
        points:pointsArr,
      });
    }
    this.Source.on('dragmove',Follow);
    this.Destination.on('dragmove',Follow);
  }
  getArrow(){return this.arrow;}
  getSource(){return this.Source;}
  getDestination(){return this.Destination;}
}
