import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { LoginService } from "../login/login.service";

/**
 * Service to manage routing for loggined users
 */
@Injectable()
export class LoggedInGuard implements CanActivate {

  constructor(private auth:LoginService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):boolean {
    return this.auth.isAuthenticated();
  }

}
