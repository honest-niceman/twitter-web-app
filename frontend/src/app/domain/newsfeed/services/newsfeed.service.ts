import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {newsfeed} from "../newsfeed";

@Injectable({
  providedIn: 'root'
})
export class NewsfeedService {

  private url = "http://localhost:8080/api/v1/newsfeed";

  constructor(private http: HttpClient) {
  }

  getNewsFeed() {
    return this.http.get<Array<newsfeed>>(
      this.url
    );
  }
}
