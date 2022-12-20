import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UserService} from "../../services/user.service";
import {user} from "../../user";

@Component({
  selector: 'app-add-dialog',
  templateUrl: './user-dialog.component.html',
  styleUrls: ['./user-dialog.component.css']
})
export class UserDialogComponent implements OnInit {
  form: FormGroup;
  actionBtn: string = "Add";
  submitClick: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<UserDialogComponent>,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public editData: user,
    private userService: UserService) {
    this.form = this.fb.group({
      id: [null],
      username: [null, [Validators.required]],
      email: [null, [Validators.required]],
      role: [null, [Validators.required]],
      password: [null, [Validators.required]]
    });
  }

  ngOnInit(): void {
    if (this.editData) {
      this.actionBtn = "Save";
      this.form.controls["id"].setValue(this.editData.id);
      this.form.controls["username"].setValue(this.editData.username);
      this.form.controls["email"].setValue(this.editData.email);
      this.form.controls["role"].setValue(this.editData.role);
      this.form.controls["password"].setValue(this.editData.password);
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
    this.userService.createUser(this.form.value).subscribe({
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
    this.userService.updateUser(this.form.value).subscribe(
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
