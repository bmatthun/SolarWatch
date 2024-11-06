import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Form from "../components/Form";

export default function LoginForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  async function fetchSignIn(username, password) {
    const res = await fetch('/api/logger/signin', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password })
    });
    const resData = await res.json();

    if (resData.jwt) {
      localStorage.setItem('jwt', resData.jwt);
      localStorage.setItem('roles', JSON.stringify(resData.roles));
    }

    return resData;
  }

  async function onSubmit(e) {
    e.preventDefault();
    const res = await fetchSignIn(username, password);

    if (localStorage.jwt) {
      if (localStorage.roles.includes('ADMIN')) {
        navigate("/admin");
      } else {
        navigate("/search");
      }
    } else {
      console.error(`Error: ${res}`);
    }
  }

  return (
    <Form 
      title="Login" 
      onSubmit={onSubmit} 
      username={username} 
      setUsername={setUsername} 
      password={password} 
      setPassword={setPassword} 
    />
  );
}
