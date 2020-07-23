import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  private URL = 'http://localhost:8080'

  constructor(private httpclient: HttpClient) {  }

  public generateToken(request) {
    return this.httpclient.post(
      this.URL + '/login',
      request,
      {observe: 'response'}
    )
  }

  public getRequest(route, token) {
    return this.httpclient.get(this.URL + '/api' + route, {headers: this.getPreparedHeaders(token)})
  }

  public postRequest(route, token, body) {
    return this.httpclient.post(this.URL + '/api' + route, body,{headers: this.getPreparedHeaders(token)})
  }

  private getPreparedHeaders(token) {
    return new HttpHeaders().set('Authorization', token)
  }

}
