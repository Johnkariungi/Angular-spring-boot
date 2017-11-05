import {Component, OnInit} from "@angular/core";
import {FormControl, FormGroup} from "@angular/forms";
import {Http, Response, Headers, RequestOptions} from "@angular/http";
import { Observable} from "rxjs/Rx";
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit{

    constructor(private http:Http){}

    private baseUrl:string = 'http://localhost:8080';

    public submitted: boolean; /*track if form was submitted */
    roomsearch : FormGroup;
    rooms: Room[]; /*define an array of rooms*/

    /*keep track of the form for post requests to capture the current values from the form*/
    request:ReserveRoomRequest;
    currentCheckInVal:string;
    currentCheckOutVal:string;

    /*methods*/

    ngOnInit() {
        this.roomsearch = new FormGroup({
            checkin: new FormControl(''),
            checkout: new FormControl('')
        });
      /*this.rooms = ROOMS; *//*set rooms to hard-coded JSON*/

      /*subscribe to changes on the form-group*/
      const roomsearchValueChanges$ = this.roomsearch.valueChanges;

      roomsearchValueChanges$.subscribe( valChange => {
          this.currentCheckInVal = valChange.checkin; /*set it equal to the value on that form field*/
          this.currentCheckOutVal = valChange.checkout;
        }
      )
    }

    onSubmit({value, valid} : {value:RoomSearch, valid:boolean}) { /*method to trigger when form is submitted*/
        //console.log(value);
        this.getAll()
        .subscribe(
            rooms => this.rooms = rooms,
            err => {
              console.log(err);
            }
          )
    }

    reserveRoom(value:string) {
      console.log("Room id for reservation" + value);
      /*create request body passing in parameter values from the form*/
      this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);
    }

    createReservation(body: ReserveRoomRequest) {
      let bodyString = JSON.stringify(body); /*convert to json*/
      let headers = new Headers({'Content-Type' : 'application/json'}); /*pass some headers into an array*/
      let option = new RequestOptions({ headers : headers}); /*add the headers*/

      /*use http module to execute a post passing the baseUrl,body & headers*/
      this.http.post(this.baseUrl + '/room/reservation/v1', body, option)
      .subscribe(res => console.log(res));
    }

    getAll() : Observable<Room[]> { /*handles get requests*/
      return this.http
      .get(this.baseUrl + '/room/reservation/v1?checkin=' + this.currentCheckInVal + '&checkout=' + this.currentCheckOutVal)
      .map(this.mapRoom);
    }

    mapRoom(response:Response): Room[] {
      return response.json().content;
    }
 }

export interface RoomSearch {
    checkin:string;
    checkout:string;
}

export interface Room {
    id:string;
    roomNumber:string;
    price:string;
    links:string;
}
/*return hard coded JSON containing an array of rooms similar from a call to the back-end rest API*/
/*var ROOMS:Room[] = [
    {
        "id": "38932123",
        "roomNumber" : "409",
        "price" : "20",
        "links" : ""
    },
    {
        "id": "8332123",
        "roomNumber" : "410",
        "price" : "25",
        "links" : ""
    },
    {
        "id": "123932123",
        "roomNumber" : "411",
        "price" : "28",
        "links" : ""
    }
];*/

export class ReserveRoomRequest {
  roomId:string;
  checkin:string;
  checkout:string;

  constructor(roomId:string,checkin:string,checkout:string) {
      this.roomId = roomId;
      this.checkin = checkin;
      this.checkout = checkout;
    }
}
