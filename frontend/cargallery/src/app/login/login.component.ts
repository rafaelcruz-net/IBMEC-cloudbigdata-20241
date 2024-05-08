import { Component } from '@angular/core';
import { Usuario } from '../model/usuario';
import { UsuarioService } from '../services/usuario.service';
import { Router } from '@angular/router';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {

  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);

  constructor(private usuarioService: UsuarioService, private router: Router) {}

  public login() {

    if (this.email.invalid || this.password.invalid) {
      return;
    }

    this.usuarioService
      .login(this.email.value as String, this.password.value as String)
      .subscribe((response) => {
        sessionStorage.setItem("user_autenticated", JSON.stringify(response));
        this.router.navigateByUrl("/home");
      });
  }
}
