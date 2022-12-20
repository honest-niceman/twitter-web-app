import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {post} from "../post";

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private url = "http://localhost:8080/api/v1/post";

  constructor(private http: HttpClient) {
  }

  getPosts() {
    return this.http.get<Array<post>>(
      this.url + '/find-all',
      {params: {pageNumber: 0, pageSize: 10}}
    );
  }

  getPost(id: number | string) {
    return this.http.get<post>(this.url + '/find' + id);
  }

  createPost(post: post) {
    const myHeaders = new HttpHeaders().set("Content-Type", "application/json");
    return this.http.post<post>(this.url + '/save', JSON.stringify(post), {headers: myHeaders});
  }

  updatePost(post: post) {
    const myHeaders = new HttpHeaders().set("Content-Type", "application/json");
    return this.http.post<post>(this.url + '/update', JSON.stringify(post), {headers: myHeaders});
  }

  deletePost(id: number | string) {
    return this.http.delete<post>(this.url + '/delete', {params: {id: id}});
  }
}
