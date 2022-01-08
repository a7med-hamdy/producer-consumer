import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
@Injectable({
  providedIn: 'root'
})
export class RequestsService {

  Headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  url = "http://localhost:8080";
  
  constructor(private http: HttpClient){}

/**************************************************
 * Craeation board requests                       *
 **************************************************/
  // > Queue < creation request
  addQueue(){
    this.http.post<any>(`${this.url}/+Q`, {})
    .subscribe(done => {
      if(done) console.log("Queue added!!")
      else     console.log("Error!! Queue wasn't added.")
    })
  }

  // > Machine < creation request
  addMachine(){
    this.http.post<any>(`${this.url}/+M`, {})
    .subscribe(done => {
      if(done) console.log("Machine added!!")
      else     console.log("Error!! Machine wasn't added.")
    })
  }
  
  // > Arrow < creation request
  addArrow(from: string, to: string){
    let info = `/${from}/${to}`
    this.http.post<any>(`${this.url}/+shm${info}`, {})
    .subscribe(done => {
      if(done) console.log("Arrow added!!")
      else     console.log("Error!! Arrow wasn't added.")
    })
  }

/**************************************************
 * Demolition requests                            *
 **************************************************/
  // clear the whole graph request
  clear(){
    this.http.delete<any>(`${this.url}/clear`)
    .subscribe(done => {
      if(done) console.log("Graph deleted!!")
      else     console.log("Error!! Graph wasn't deleted.")
    })
  }

/**************************************************
 * Simulation requests                            *
 **************************************************/
  // start simulation request
  play(){
    this.http.post<any>(`${this.url}/play`, {})
    .subscribe(done => {
      if(done) console.log("Simulation started!!")
      else     console.log("Error!! Simulation wasn't started.")
    })
  }

  // stop simulation request
  stop(){
    this.http.post<any>(`${this.url}/stop`, {})
    .subscribe(done => {
      if(done) console.log("Simulation stopped!!")
      else     console.log("Error!! Simulation wasn't stopped.")
    })
  }

  // replay a specified simulation request 
  replay(id: number){
    this.http.post<any>(`${this.url}/replay/${id}`, {})
    .subscribe(done => {
      if(done) console.log(`Simulation ${id} replayed!!`)
      else     console.log(`Error!! Simulation ${id} wasn't replayed.`)
    })
  }
}
