import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {SelectionModel} from "@angular/cdk/collections";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatDialog} from "@angular/material/dialog";
import {PostDialogComponent} from "./post-dialog/post-dialog.component";
import {TokenStorageService} from "../../../auth/services/token-storage.service";
import {PostService} from "../services/post.service";
import {post} from "../post";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['select', 'date', 'text', 'attachmentUrl'];
  dataSource: MatTableDataSource<post> = new MatTableDataSource<post>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  selection = new SelectionModel<post>(true, []);

  role: string | undefined;

  constructor(public dialog: MatDialog,
              private postService: PostService,
              private tokenStorage: TokenStorageService) {
  }

  ngOnInit(): void {
    this.role = this.tokenStorage.getUser().role;
    this.getAllData();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: post): string {
    return `${this.selection.isSelected(<post>row) ? 'deselect' : 'select'}`;
  }

  //---------------------------------------------------

  getAllData() {
    this.postService.getPosts()
      .subscribe({
        next: data => {
          this.dataSource.data = data;
        },
        error: err => {
          alert(err.message);
          return [];
        }
      });
  }

  openAddDataDialog() {
    this.dialog.open(PostDialogComponent).afterClosed().subscribe((data) => {
      if (data) {
        this.getAllData();
      }
    });
  }

  openUpdateDataDialog() {
    this.dialog.open(PostDialogComponent, {
      data: this.selection.selected[0]
    }).afterClosed().subscribe(data => {
      if (data) {
        this.getAllData();
        this.selection.clear();
      }
    });
  }

  openDeleteDataDialog() {
    this.postService.deletePost(this.selection.selected[0]?.id).subscribe({
      next: () => {
        this.getAllData();
        this.selection.clear();
      },
      error: err => {
        alert("Error " + err.message);
      }
    });
  }
}
