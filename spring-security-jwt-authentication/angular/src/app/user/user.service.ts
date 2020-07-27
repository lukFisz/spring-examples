import {Injectable} from '@angular/core';
import {JwtClientService} from "../jwt/jwt-client.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private jwt: JwtClientService) {
  }

  login(auth) {
    return this.jwt.generateToken(auth)
  }

  logout() {
    this.jwt.destroy();
    return this.jwt.post('/logout')
  }

  getUser() {
    return this.jwt.get('/user')
  }

  getAllUsers() {
    return this.jwt.get('/users')
  }

  newUser(user) {
    return this.jwt.post('/user/add', user)
  }

}
