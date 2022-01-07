import Konva from 'konva'
export class ShapeWithText{
  shape!:Konva.Shape;
  text!:Konva.Text;
  Group!:Konva.Group;
  animator!:Konva.Tween;
  BackArrows: any[] = [];
  FrontArrows: any[] = [];

  constructor(shape:Konva.Shape, text:Konva.Text, BackArrows:any[],FrontArrows:any[]){
    this.shape = shape
    this.text = text
    this.BackArrows = BackArrows;
    this.FrontArrows = FrontArrows;
    var component = this;
    this.animator = new Konva.Tween({
      node: component.shape,
      duration: 0.5,
      easing: Konva.Easings.EaseInOut,
      onFinish:() => this.animator.reverse(),
      fill: 'rgb(0, 5, 0)',
    });
    this.Group = new Konva.Group({
      draggable: true,
      offsetX:this.shape.x(),
      offsetY:this.shape.y()
    });
    this.Group.add(this.shape);
    this.Group.add(this.text);
  }

  getShapeWithText(){return this.Group;}
  getShape(){return this.shape}
  addFollowerToFront(arrow:any){
    this.FrontArrows.push(arrow);
  }
  addFollowerToBack(arrow:any){
    this.BackArrows.push(arrow);
  }
  playAnimation(){
    this.animator.play();
    var comp = this;
  }
  playRevAnimation(){
    this.animator.reverse();
  }
}
