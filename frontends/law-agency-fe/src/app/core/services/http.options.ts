import {HttpHeaders} from "@angular/common/http";

export const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': '*/*'
  })
}
export const headers = new HttpHeaders().set('Accept', '*/*').set('Content-Type', 'application/json')
