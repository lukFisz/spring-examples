import { Component, OnInit } from '@angular/core';
import {JwtClientService} from "../jwt/jwt-client.service";

@Component({
  selector: 'app-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.css']
})
export class SecurityComponent implements OnInit {

  auth: any = {
    'username':'user',
    'password':'user123'
  };

  token: any

  constructor(private jwtService: JwtClientService) { }

  ngOnInit(): void {
    this.getAccessToken(this.auth).subscribe(value => {
      this.token = value.headers.get('Authorization');
    });
  }

  private getAccessToken(authRequest) {
    return this.jwtService.generateToken(authRequest);
  }

  user() {
    this.jwtService.getUser(this.token).subscribe(value => console.log(value))
  }

  users() {
    this.jwtService.getAllUsers(this.token).subscribe(value => console.log(value));
  }
}
