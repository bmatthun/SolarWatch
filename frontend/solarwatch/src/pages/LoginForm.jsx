import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function LoginForm() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    async function fetchSignIn(username, password) {
        const res = await fetch('api/logger/signin', {
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

         if (resData.jwt) {
          localStorage.setItem('jwt', resData.jwt)
          localStorage.setItem('roles', resData.roles)
         }

         return resData;
    }

    async function onSubmit(e) {
        e.preventDefault()
        const res = await fetchSignIn(username, password);

        console.log(localStorage);
        
        if (localStorage.jwt && localStorage.roles.includes('ADMIN')) {
          navigate("/admin")
        } else if (localStorage.jwt) {
          navigate("/search")  
        }
        else {
          console.error(`Error: ${res}`)
        }
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
            <button type="submit">Login</button>
        </form>
      );
    };
