import { Component } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component ({
	templateUrl: '/resources/static/app/main/main.component.html'
})

export class MainComponent {
	private id: number;
	constructor() {
		this.id = 1;
	}
}