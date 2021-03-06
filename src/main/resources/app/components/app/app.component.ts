import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../services/httpServices/authenticationServices/authentication.service";
import {HttpService} from "../../services/httpServices/http.service";
import {Cookies} from "../../cookies";

@Component({
    selector : 'my-app',
    templateUrl: 'app/components/app/app.component.html',
    styleUrls: ['app/components/app/app.component.css']
})

export class MyApp {

    constructor(private authService: AuthenticationService,
                private http: HttpService) {
    }

    public logout() {
        this.http.deleteToken();
        let cookies: Cookies = new Cookies();
        cookies.deleteCookie("token");
    }
}