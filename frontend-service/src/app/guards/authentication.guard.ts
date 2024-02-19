import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {Observable} from "rxjs";
import {AppStateService} from "../app.state.service";
import {Injectable} from "@angular/core";
import {Service} from "../services/Service";

@Injectable({
  providedIn: "root"
})
export class AuthenticationGuard implements CanActivate{
  constructor(private appStateService: AppStateService, private router: Router, private service: Service) {
  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.appStateService.authState.isAuthenticated || this.service.getTokenFromLocalStorage())
      return true;
    this.router.navigateByUrl("/login");
    return false;
  }

}
