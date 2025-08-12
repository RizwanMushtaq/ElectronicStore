import { HttpClient } from '@angular/common/http';
import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatButtonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly title = signal('AngularApp');

  constructor(private http: HttpClient) {}

  public loadProducts() {
    console.log('loading products');
    this.http.get<any>('http://localhost:8080/api/products').subscribe({
      next: (data) => {
        console.log(data);
      },
      error: (error) => {
        console.log('Error loading products', error);
      },
    });
  }
}
