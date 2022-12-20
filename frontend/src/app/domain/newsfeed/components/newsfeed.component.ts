import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {TokenStorageService} from "../../../auth/services/token-storage.service";
import {newsfeed} from "../newsfeed";
import {NewsfeedService} from "../services/newsfeed.service";

@Component({
  selector: 'app-card-fancy-example',
  templateUrl: './newsfeed.component.html',
  styleUrls: ['./newsfeed.component.css']
})
export class NewsfeedComponent implements OnInit{
  role: string | undefined;
  newsfeeds: Array<newsfeed> = new Array<newsfeed>();
  currentUserId: number;

  constructor(public dialog: MatDialog,
              private newsfeedService: NewsfeedService,
              private tokenStorage: TokenStorageService) {
  }

  ngOnInit(): void {
    this.role = this.tokenStorage.getUser().role;
    this.currentUserId = this.tokenStorage.getUser().id;
    this.getAllData();
  }

  getAllData() {
    this.newsfeedService.getNewsFeed()
      .subscribe({
        next: data => {
          this.newsfeeds = data;
        },
        error: err => {
          alert(err.message);
          return [];
        }
      });
  }
}
