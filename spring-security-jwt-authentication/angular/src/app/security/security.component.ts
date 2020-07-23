import {Component, OnInit} from '@angular/core';
import {UserService} from "../user/user.service";

@Component({
  selector: 'app-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.css']
})
export class SecurityComponent implements OnInit {

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

  token: any

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService
      .login(this.auth)
      .subscribe(value => {
        this.token = value.headers.get('Authorization');
      });
  }

  user() {
    this.userService
      .getUser(this.token)
      .subscribe(value => console.log(value))
  }

  users() {
    this.userService
      .getAllUsers(this.token)
      .subscribe(value => console.log(value));
  }

  newUser() {
    this.userService
      .newUser(this.token, this.newuser)
      .subscribe(value => console.log(value));
  }

}
