import { Component } from '@angular/core';

import axios from 'axios';

import { FormsModule } from '@angular/forms';

import { Router } from '@angular/router';

@Component({

  selector: 'app-login',

  standalone: true,

  imports: [FormsModule],

  templateUrl: './login.html'
})
export class LoginComponent {

  username = '';

  password = '';

  constructor(private router: Router) {}

  async login() {

    try {

      const response = await axios.post(

        'http://localhost:8080/api/auth/login',

        {
          username: this.username.trim(),
          password: this.password
        }
      );

      localStorage.setItem(
        'access_token',
        response.data.access_token
      );

      alert('Login success');

      this.router.navigate(['/dashboard']);

    } catch(error) {

      console.log(error);

      alert('Login failed');
    }
  }
}