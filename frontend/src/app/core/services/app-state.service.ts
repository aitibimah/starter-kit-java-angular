import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface UserProfile {
    id: number;
    username: string;
    email: string;
    roles: string[];
}

export interface AppState {
    user: UserProfile | null;
    isDarkMode: boolean;
    language: string;
}

const initialState: AppState = {
    user: null,
    isDarkMode: false,
    language: 'en'
};

@Injectable({
    providedIn: 'root'
})
export class AppStateService {
    private state = new BehaviorSubject<AppState>(initialState);

    constructor() {
        // Load state from localStorage on init
        const savedState = localStorage.getItem('appState');
        if (savedState) {
            this.state.next(JSON.parse(savedState));
        }

        // Save state to localStorage on changes
        this.state.subscribe(state => {
            localStorage.setItem('appState', JSON.stringify(state));
        });
    }

    getState(): Observable<AppState> {
        return this.state.asObservable();
    }

    setUser(user: UserProfile | null): void {
        this.updateState({ user });
    }

    toggleDarkMode(): void {
        const currentState = this.state.value;
        this.updateState({ isDarkMode: !currentState.isDarkMode });
    }

    setLanguage(language: string): void {
        this.updateState({ language });
    }

    private updateState(partialState: Partial<AppState>): void {
        this.state.next({
            ...this.state.value,
            ...partialState
        });
    }
} 