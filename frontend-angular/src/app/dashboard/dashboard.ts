import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';

import { Router } from '@angular/router';

import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-dashboard',

  standalone: true,

  imports: [CommonModule],

  templateUrl: './dashboard.html',

  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {

  role: string = '';

  constructor(
    private router: Router
  ) {}

  ngOnInit(): void {

    const token =
      localStorage.getItem(
        'access_token'
      );

    if (token) {

      const decoded: any =
        jwtDecode(token);

      console.log(decoded);

      this.role =
        decoded?.realm_access?.roles?.[0]
        || 'USER';
    }
  }

  logout(): void {

    localStorage.clear();

    this.router.navigate(['/']);
  }
}