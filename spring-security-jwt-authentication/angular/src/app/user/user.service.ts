import {Injectable} from '@angular/core';
import {JwtClientService} from "../jwt/jwt-client.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private jwtService: JwtClientService) {
  }

  login(auth) {
    return this.jwtService.generateToken(auth)
  }

  logout() {
    return this.jwtService.getRequest('/logout', {})
  }

  getUser(token) {
    return this.jwtService.getRequest('/user', token)
  }

  getAllUsers(token) {
    return this.jwtService.getRequest('/users', token)
  }

  newUser(token, user) {
    return this.jwtService.postRequest('/user/add', token, user)
  }


}
