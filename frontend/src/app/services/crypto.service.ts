import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable({
  providedIn: 'root'
})
export class CryptoService {
  private readonly salt = 'openshift-monitor-salt'; // 加盐增加安全性

  encryptPassword(password: string): string {
    // 将密码和盐值组合
    const saltedPassword = password + this.salt;
    // 使用 SHA-256 加密
    return CryptoJS.SHA256(saltedPassword).toString();
  }
} 