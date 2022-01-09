import { Component, OnInit } from '@angular/core';
import  Konva from 'konva';
import { WebSocketAPI } from '../WebSocketAPI';
import { ShapeWithText } from './shapeWithText'
import { Arrow } from './arrow';
import { RequestsService } from '../requests/requests.service';
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
  Choosing = false
  webSocketAPI!: WebSocketAPI;
  message: any;
  name: string = '';
  constructor(private req:RequestsService) { }

  ngOnInit() {
    this.webSocketAPI = new WebSocketAPI(new BoardComponent());
    //connect to backend at start
    this.connect()
    //create the stage on start
    this.stage = new Konva.Stage({
      container: 'container',
      width: window.innerWidth,
      height: window.innerHeight,
    });
    this.layer = new Konva.Layer();//create layer on start
    this.stage.add(this.layer);//add the layer to the stage on start
  }
/***************************************************************************************************************** */
  //methods for websocket
  connect(){
    this.webSocketAPI._connect();
  }

  disconnect(){
    this.webSocketAPI._disconnect();
  }

  sendMessage(){
    this.webSocketAPI._send(this.name);
  }

  handleMessage(message: string){
    console.log("handled")
    this.message = message;
  }
  //end of websocket
/*************************************************************************************************************** */


  /**
   * clears all the board
   */
  clearAll(){
    this.shapes = [];
    this.pointers = [];
    this.layer.destroyChildren();
    this.numOfQs = 0;
    this.numOfMs = 0;
    console.log(this.layer.getChildren());
  }

  /**
   * filter shapes array according to given element
   *
   * @param criteria group to be filtered with
   * @returns filtered array
   */
  getShapeWithTextFromArray(criteria:any){
    var x = this.shapes.filter(function(element){
      return element.getShapeWithText() == criteria;
    });
    return x;
  }
  /**
   * filter shapes array according to given name
   *
   * @param name name of the shape element
   * @returns filtered array
   */
  getShapeWithTextFromArrayByName(name:string){
    var x = this.shapes.filter(function(element){
      return element.getShapeWithText().getShape().name() == name;
    });
    return x;
  }


  /** Adds either M or Q to the board
   *
   * @param string M | Q
   */
  add(string:string){
    var shape;
    var text1;
    var text2;
    var color;
    //if M
    if(string == 'M'){
      ////////////////////////this.req.addMachine();////////////////////////////////
      color = 'green'
      console.log("add Ms")
      shape = new Konva.Circle({
        name: 'M'+this.numOfMs.toString(),
        x:300,
        y:300,
        radius:50,
        fill:'green',
      });
      text1 = new Konva.Text({
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
      this.numOfMs++

    }
    //if Q
    else{
      /////////////////////////////this.req.addQueue()///////////////////////////////
      color = 'yellow'
      console.log("addQs")
      shape = new Konva.Rect({
        name:'Q'+this.numOfQs.toString(),
        x:300,
        y:300,
        width:100,
        height:50,
        fill:'yellow',
      });
      shape.setAttrs({
        offset:{x:shape.getAttr('width')/2,
                y:shape.getAttr('height')/2
        }
      })
      text1 = new Konva.Text({
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
      text2 = new Konva.Text({
        offset:{x:shape.getAttr('offsetX')*1.5,
                y:shape.getAttr('offsetY')*1.5
        },
      x:shape.getAttr('x'),
      y:shape.getAttr('y'),
      fill:'black',
      fontFamily:'Consolas',
      fontSize:20,
      text: '0'
    });
    this.numOfQs++
    }
    var FrontArrows: any[] = [];
    var BackArrows: any[] = [];

    var SwithT = new ShapeWithText(shape,text1,text2,BackArrows,FrontArrows,color,0);
    this.shapes.push(SwithT);
    this.layer.add(SwithT.getShapeWithText())
  }


  /**
   * adds arrows between two shapes
   */
  addArrow(){
    console.log("add Arrows!");
    var arrow;
    var source:any;
    var destination:any;
    var clicks = 0;
    this.Choosing = true
    var component = this;
    this.stage.on("click",function(e){
      clicks++;
      console.log(e.target)
      //if the clicked shape is a konva shape
      if(e.target instanceof Konva.Shape){
        console.log("Hi")
        //gets its source group
        if (clicks == 1){
          console.log("1stClick")
          source = e.target.getParent();
        }
        //gets its destination group
        if(clicks == 2){
          console.log("2ndClick")
          destination = e.target.getParent();
        }
      }
      if(clicks >= 2){
        console.log("Two clicks")
        if(source != null && destination != null && source != destination){
          console.log("adding arrows!")
          arrow = new Arrow(source,destination);      //create new arrow component
          //get the source and destination shapes
          var x = component.getShapeWithTextFromArray(source);
          var y = component.getShapeWithTextFromArray(destination);

          if(source.name().includes('M')){
            var followers = x[0].getFollowersOut();
            var f = followers.filter(function(element:any){
              return element.getDestination().name().includes('Q');
            });
            if(f.length != 0){
              component.Choosing=false
              component.stage.off('click');
              return
            }
          }
          /////////////////////////////requestLine/////////////////////////////////////////////////////
          //component.req.addArrow(source.name(),destination)

          //add the arrow to the shapes's arrays
          x[0].addFollowerOut(arrow);
          y[0].addFollowerIn(arrow);
          var num = x[0].getProductsNumber();
          x[0].updateProductsNumber(num+1);
          x[0].playFlashAnimation();

          component.pointers.push(arrow);
          component.layer.add(arrow.getArrow());//add arrow to the layer to display
          //console.log(x[0].FrontArrows)

          //console.log(y[0].BackArrows)
        }
        component.Choosing=false
        component.stage.off('click');
      }
    });

  }

}
