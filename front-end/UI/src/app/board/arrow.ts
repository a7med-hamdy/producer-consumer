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
      this.Source.x()+25,
      this.Source.y()+25,
      this.Destination.x()-25,
      this.Destination.y()-25
    ],
    fill:'black',
    stroke:'black'

    });
    var component = this;
    function Follow(){
      const pointsArr = [
        component.Source.x()+25,
        component.Source.y()+25,
        component.Destination.x()-25,
        component.Destination.y()-25,
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
