import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from "./user.service";
import {JwtClientService} from "../jwt/jwt-client.service";
import {Principal} from "../models/principal";

@Component({
  selector: 'app-security',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent implements OnInit {

  @ViewChild('div') div;

  principal: Principal;

  auth: any = {
    'username': 'user',
    'password': 'user123'
  };

  newuser: any = {
    username: 'user2',
    password: 'user123',
    role: 'USER',
    active: true
  }

  constructor(private userService: UserService) {  }

  ngOnInit(): void {
    this.userService.login(this.auth);
  }

  user() {
    this.userService
      .getUser()
      .subscribe(value => this.insert(value))
  }

  users() {
    this.userService
      .getAllUsers()
      .subscribe(value => console.log(value));
  }

  newUser() {
    this.userService
      .newUser(this.newuser)
      .subscribe(value => console.log(value));
  }

  insert(content) {
    this.principal = content as Principal;
  }


}
