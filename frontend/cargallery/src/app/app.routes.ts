import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { UserAutenticatedGuard } from './guard/user-autenticated.guard';
import { FavoritoComponent } from './favorito/favorito.component';

export const routes: Routes = [
    {
        path: '', 
        redirectTo: "home",
        pathMatch: "full"
    }, 
    {
        path: 'home',
        component: HomeComponent,
        canActivate:[UserAutenticatedGuard]
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'favoritos',
        component: FavoritoComponent,
        canActivate:[UserAutenticatedGuard]

    },
    
];
