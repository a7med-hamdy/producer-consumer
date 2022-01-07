import Konva from 'konva'
import { delay } from 'rxjs';
export class ShapeWithText{
  shape!:Konva.Shape;
  text!:Konva.Text;
  Group!:Konva.Group;
  animator!:Konva.Tween;
  Color!:string;
  InArrows: any[] = [];
  OutArrows: any[] = [];

  /**
   *
   * @param shape shape Rectangle or Circle
   * @param text  Text to be put on the Rectangle
   * @param InArrows Arrows pointing towards the shape
   * @param OutArrows Arrows pointing out of theshape
   * @param Color Original Color of the shape
   */
  constructor(shape:Konva.Shape, text:Konva.Text, InArrows:any[],OutArrows:any[],Color:string){
    this.shape = shape
    this.text = text
    this.InArrows = InArrows;
    this.OutArrows = OutArrows;
    this.Color = Color;

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
  addFollowerIn(arrow:any){
    this.InArrows.push(arrow);
  }
  addFollowerOut(arrow:any){
    this.OutArrows.push(arrow);
  }
  private sleep(ms:any){
    return new Promise(
      resolve => setTimeout(resolve, ms)
    );
  }
  async playFlashAnimation(){
    this.animator = new Konva.Tween({
      node: this.shape,
      duration: 0.3,
      easing: Konva.Easings.BackEaseInOut,
      fill: 'rgb(255, 255, 255)',
    });
    var component = this
    for(var i = 0; i < 3;i++){
      this.animator.play()
      await this.sleep(300);
      this.animator.reverse();
      await this.sleep(300);
    }
  }
  playColorAnimation(color:string){
    this.animator = new Konva.Tween({
      node: this.shape,
      duration: 0.3,
      easing: Konva.Easings.BackEaseInOut,
      fill: color,
    });
    this.animator.play();
  }
  playReverseColorAnimation(){
    this.playColorAnimation(this.Color);
  }
}
