import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {CommentInterface} from '../types/comment.interface';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class CommentsService {
  constructor(private httpClient: HttpClient) {
  }

  getComments(postId: string): Observable<CommentInterface[]> {
    return this.httpClient.get<CommentInterface[]>(
      'http://localhost:8080/api/v1/comment/find-all',
      {params: {pageNumber: 0, pageSize: 5, postId: postId}}
    );
  }

  createComment(
    text: string,
    parentId: string | null = null,
    postId: string
  ): Observable<CommentInterface> {
    return this.httpClient.post<CommentInterface>(
      'http://localhost:8080/api/v1/comment/save',
      {
        body: text,
        parentId: parentId,
        postId: postId
      }
    );
  }
}
