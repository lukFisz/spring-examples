import {Component, OnInit} from '@angular/core';
import {JwtClientService} from '../jwt/jwt-client.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent implements OnInit {

  isTokenCreatedMsg: any = 'Token missing';
  allUsers: any;
  selectedLogin: any;
  currentUser: any;
  receivedData: any;
  username: string = null;
  userType: any = null;

  constructor(public jwt: JwtClientService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.logout();
    this.jwt.get('/users').subscribe(value => this.allUsers = value);
  }

  login() {
    if (this.selectedLogin !== undefined) {
      const user = this.allUsers[this.selectedLogin - 1];
      this.currentUser = user;
      this.jwt.generateToken({username: user.username, password: user.username},
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
        this.receivedData = value;
      }, error => {
        this.receivedData = error;
      });
  }

  users() {
    this.jwt.get('/users')
      .subscribe(value => this.receivedData = value);
  }

  showToken() {
    const msg = this.jwt.token() !== null ? this.jwt.token() : 'Token missing';
    window.alert(msg);
  }

  admin() {
    this.jwt.get('/admin').subscribe(
      value => this.receivedData = value,
        error => this.receivedData = error
    );
  }

  openModal(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }

  addUser() {
    if (this.username === null || this.userType === null){
      this.receivedData = 'Username or/and role missing.';
    } else if (this.jwt.isTokenStored()){
      const newUserData = {
        username: `${this.username}`,
        role: this.userType
      };
      this.jwt.post(
        '/admin/add/user',
        newUserData
      ).subscribe(value => {
          this.allUsers = value.body;
          this.receivedData = `User ${newUserData.username} successfully created.`;
        }, error => {
          this.receivedData = error;
        });
    } else {
      this.receivedData = 'Please log in as an administrator';
    }
    this.username = null;
    this.userType = null;
    this.modalService.dismissAll();
  }
}
