import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {PostService} from "../../services/post.service";
import {post} from "../../post";

@Component({
  selector: 'app-post-add-dialog',
  templateUrl: './post-dialog.component.html',
  styleUrls: ['./post-dialog.component.css']
})
export class PostDialogComponent implements OnInit {
  form: FormGroup;
  actionBtn: string = "Add";
  submitClick: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<PostDialogComponent>,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public editData: post,
    private postService: PostService) {
    this.form = this.fb.group({
      id: [null],
      date: [null, [Validators.required]],
      text: [null, [Validators.required]],
      attachmentUrl: [null, [Validators.required]]
    });
  }

  ngOnInit(): void {
    if (this.editData) {
      this.actionBtn = "Save";
      this.form.controls["id"].setValue(this.editData.id);
      this.form.controls["date"].setValue(this.editData.date);
      this.form.controls["text"].setValue(this.editData.text);
      this.form.controls["attachmentUrl"].setValue(this.editData.attachmentUrl);
    }
  }

  onSubmit(): void {
    if (!this.editData) {
      this.addData();
    } else {
      this.updateData();
    }
  }

  addData() {
    this.postService.createPost(this.form.value).subscribe({
      next: () => {
        this.submitClick = true;
        this.onClose()
      },
      error: err => {
        alert("Error! " + err.message);
      }
    });
  }

  updateData() {
    this.postService.updatePost(this.form.value).subscribe(
      {
        next: () => {
          this.submitClick = true;
          this.onClose()
        },
        error: err => {
          alert("Error! " + err.message);
        }
      });
  }

  onClose() {
    this.dialogRef.close(this.submitClick);
  }
}
