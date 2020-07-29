import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {JwtClientService} from "../jwt/jwt-client.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent implements OnInit {

  isTokenCreatedMsg: string = 'Token missing';
  allUsers: any;
  selectedLogin: any;
  currentUser: any;
  receivedData: any;
  newUserUsername: any;
  newUserPassword: any;
  newUserRole: any;
  formGroup: any;

  constructor(public jwt: JwtClientService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.logout();
    this.jwt.get('/users').subscribe(value => this.allUsers = value);
  }

  login() {
    if (this.selectedLogin !== undefined) {
      let user = this.allUsers[this.selectedLogin - 1];
      this.currentUser = user;
      this.jwt.generateToken({username: user.username, password: `${user.username}123`},
        () => {
          this.isTokenCreatedMsg = `Token created (click to display)`;
      });
    }
  }

  logout() {
    this.jwt.destroy();
    this.isTokenCreatedMsg = 'Token missing';
    this.currentUser = null;
    this.receivedData = {};
  }

  user() {
    this.jwt.get('/user')
      .subscribe(value => {
        this.receivedData = value
      }, error => {
        this.receivedData = error;
      });
  }

  users() {
    this.jwt.get('/users')
      .subscribe(value => this.receivedData = value);
  }

  showToken() {
    let msg = this.jwt.token() !== null ? this.jwt.token() : 'Token missing'
    window.alert(msg)
  }

  admin() {
    this.jwt.get('/admin').subscribe(
      value => this.receivedData = value,
        error => this.receivedData = error
    );
  }

  newUser(newUserModal: TemplateRef<any>) {
  }

  openNewUserModal(content) {
    this.modalService.open(content)
  }


}
