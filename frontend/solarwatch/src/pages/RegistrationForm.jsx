import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function RegistrationForm() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    async function fetchRegData(username, password) {
        const res = await fetch('/api/logger/register', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json',
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        })
        const resData = await res.json();
        console.log(resData);
        
    }

    function onSubmit(e) {
        e.preventDefault();
        fetchRegData(username, password);
        navigate("/signin");
    }

    return  (
        <form className="RegistartionForm" onSubmit={(e) => onSubmit(e)}>
          <div className="control">
            <label htmlFor="username">Username:</label>
            <input
              value={username}
              type="text"
              onChange={(e) => setUsername(e.target.value)}
              name="username"
              id="username"
            />
          </div>
    
          <div className="control">
            <label htmlFor="password">Password:</label>
            <input
              value={password}
              type="password"
              onChange={(e) => setPassword(e.target.value)}
              name="password"
              id="password"
            />
          </div>
            <button type="submit">Register</button>
        </form>
      );
    };
