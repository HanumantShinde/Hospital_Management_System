import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-unauthorized',
  standalone: true,
  templateUrl: './unauthorized.component.html',
  styleUrl: './unauthorized.component.css'
})
export class UnauthorizedComponent {

  constructor(private authService: AuthService, private router: Router) {}

  goBack(): void {
    const role = this.authService.getRole();
    if (role === 'ADMIN') this.router.navigate(['/admin']);
    else if (role === 'DOCTOR') this.router.navigate(['/doctor']);
    else if (role === 'PATIENT') this.router.navigate(['/patient']);
    else this.router.navigate(['/login']);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
