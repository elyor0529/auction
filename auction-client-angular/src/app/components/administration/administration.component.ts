import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';
import { User } from '../../models/user';
import { UserService } from '../../services/user/user.service';
import { TranslateService } from '@ngx-translate/core';
import { InteractionService } from '../../services/interaction/interaction.service';

/**
 * Component view /administration page
 */
@Component({
  selector: 'app-administration',
  templateUrl: './administration.component.html',
  styleUrls: ['./administration.component.css']
})
export class AdministrationComponent implements OnInit {

  users: User[];
  modalRef: BsModalRef;
  selectedUser: User;

  constructor(
    public interact: InteractionService,
    private userService: UserService,
    private modalService: BsModalService,
    private translate: TranslateService
  ) { }

  ngOnInit() {
    this.getUsers();
  }

  getUsers() {
    this.userService.getAllUsers().subscribe(res => {
      this.users = res;
    })
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template, {class: 'modal-sm'});
  }

  confirmDelete(): void {
    this.userService.deleteUser(this.selectedUser.userName).subscribe(() => {
      this.users.splice(this.users.indexOf(this.selectedUser),1);
      this.modalRef.hide();
    });
  }

  declineDelete(): void {
    this.modalRef.hide();
  }

  enableUser(user: User) {
    this.userService.enableUser(user.userName).subscribe(res => {
      this.users[this.users.indexOf(user)] = res;
    })
  }

  disableUser(user: User) {
    this.userService.disableUser(user.userName).subscribe(res => {
      this.users[this.users.indexOf(user)] = res;
    })
  }

  promoteUser(user: User) {
    this.userService.promoteUser(user.userName).subscribe(res => {
      this.users[this.users.indexOf(user)] = res;
    })
  }

  demoteUser(user: User) {
    this.userService.demoteUser(user.userName).subscribe(res => {
      this.users[this.users.indexOf(user)] = res;
    })
  }

  changePassword(user) {
    this.interact.callPasswordChangeModal(user);
  }

  isManager(user: User) {
    return user.roles.find(r => r.role == 'ROLE_MANAGER');
  }

  isAdmin(user: User) {
    return user.roles.find(r => r.role == 'ROLE_ADMIN');
  }

}
