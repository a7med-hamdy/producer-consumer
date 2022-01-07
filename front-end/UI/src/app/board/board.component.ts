import { Component, OnInit } from '@angular/core';
import  Konva from 'konva';
import { ShapeWithText } from './shapeWithText'
import { Arrow } from './arrow';
@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  pointers:any[] = [];
  shapes:any[] = [];
  stage!:Konva.Stage;
  layer!:Konva.Layer;
  numOfQs = 0;
  numOfMs = 0;
  constructor() { }

  ngOnInit() {
    //create the stage on start
    this.stage = new Konva.Stage({
      container: 'container',
      width: window.innerWidth,
      height: window.innerHeight,
    });
    this.layer = new Konva.Layer();//create layer on start
    this.stage.add(this.layer);//add the layer to the stage on start
  }
  clearAll(){
    this.shapes = [];
    this.pointers = [];
    this.layer.destroyChildren();
    this.numOfQs = 0;
    this.numOfMs = 0;
    console.log(this.layer.getChildren());
  }
  getShapeWithTextFromArray(criteria:any){
    var x = this.shapes.filter(function(element){
      return element.getShapeWithText() == criteria;
    });
    return x;
  }
  add(string:string){
    var shape;
    var text;
    if(string == 'M'){
      console.log("add Ms")
      this.numOfMs++
      shape = new Konva.Circle({
        x:150,
        y:150,
        radius:50,
        fill:'green',
      });
      text = new Konva.Text({
          offset:{x:shape.getAttr('radius')/8,
                  y:shape.getAttr('radius')/8
          },
        x:shape.getAttr('x'),
        y:shape.getAttr('y'),
        fill:'black',
        fontFamily:'Consolas',
        fontSize:20,
        text:'M'+this.numOfMs.toString()
      });
    }
    else{
      this.numOfQs++
      console.log("addQs")
      shape = new Konva.Rect({
        x:150,
        y:150,
        width:100,
        height:50,
        fill:'yellow',
      });
      shape.setAttrs({
        offset:{x:shape.getAttr('width')/2,
                y:shape.getAttr('height')/2
        }
      })
      text = new Konva.Text({
          offset:{x:shape.getAttr('offsetX')/4,
                  y:shape.getAttr('offsetY')/4
          },
        x:shape.getAttr('x'),
        y:shape.getAttr('y'),
        fill:'black',
        fontFamily:'Consolas',
        fontSize:20,
        text:'Q'+this.numOfQs.toString()
      });
    }
    var FrontArrows: any[] = [];
    var BackArrows: any[] = [];

    var SwithT = new ShapeWithText(shape,text,BackArrows,FrontArrows);
    this.shapes.push(SwithT);
    this.layer.add(SwithT.getShapeWithText())
  }



  addArrow(){
    console.log("add Arrows!");
    var arrow;
    var source:any;
    var destination:any;
    var clicks = 0;
    var component = this;
    this.stage.on("click",function(e){
      clicks++;
      console.log(e.target)
      if(e.target instanceof Konva.Shape){
        console.log("Hi")
        if (clicks == 1){
          console.log("1stClick")
          source = e.target.getParent();
        }
        if(clicks == 2){
          console.log("2ndClick")
          destination = e.target.getParent();
        }
      }
      if(clicks >= 2){
        console.log("Two clicks")
        if(source != null && destination != null){
          console.log("adding arrows!")
          arrow = new Arrow(source,destination);
          component.pointers.push(arrow);
          var x = component.getShapeWithTextFromArray(source);
          var y = component.getShapeWithTextFromArray(destination);
          x[0].addFollowerToFront(arrow);
          y[0].addFollowerToBack(arrow);
          x[0].playAnimation();
          y[0].playRevAnimation();
          component.layer.add(arrow.getArrow());
          console.log(x[0].FrontArrows)

          console.log(y[0].BackArrows)
        }
        component.stage.off('click');
      }
    });

  }

}
