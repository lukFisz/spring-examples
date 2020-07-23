import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  constructor(private httpclient: HttpClient) {  }

  public generateToken(request) {
    return this.httpclient.post(
      "http://localhost:8080/login",
      request,
      {observe: 'response'}
    )
  }

  public getUser(token) {
    return this.getRequest('http://localhost:8080/api/user', token)
  }

  public getAllUsers(token) {
    return this.getRequest('http://localhost:8080/api/users', token)
  }

  public getRequest(route, token) {
    return this.httpclient.get(route, {headers: this.getPreparedHeaders(token)})
  }

  public postRequest(route, token, body) {
    return this.httpclient.post(route, body,{headers: this.getPreparedHeaders(token)})
  }

  private getPreparedHeaders(token) {
    return new HttpHeaders().set('Authorization', token)
  }

}
