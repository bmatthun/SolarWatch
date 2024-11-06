import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Form from "../components/Form";

export default function RegistrationForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  async function fetchRegData(username, password) {
    const res = await fetch('/api/logger/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password })
    });
    const resData = await res.json();
    console.log(resData);
    return resData;
  }

  async function onSubmit(e) {
    e.preventDefault();
    await fetchRegData(username, password);
    navigate("/");
  }

  return (
    <Form 
      title="Register" 
      onSubmit={onSubmit} 
      username={username} 
      setUsername={setUsername} 
      password={password} 
      setPassword={setPassword} 
    />
  );
}
