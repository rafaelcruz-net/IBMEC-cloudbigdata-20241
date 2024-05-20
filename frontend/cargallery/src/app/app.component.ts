import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CarModalComponent } from './shared/car-modal/car-modal.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, HomeComponent, CarModalComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  user:any = undefined;

  constructor(private router: Router) {}

  ngOnInit(): void {
    if (sessionStorage["user_autenticated"])
      this.user = JSON.parse(sessionStorage["user_autenticated"]);
  }

  public favoritos() {
      this.router.navigate(["favoritos"]);
  }
}
