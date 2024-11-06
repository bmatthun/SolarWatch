import React from "react";

export default function Form({ title, onSubmit, username, setUsername, password, setPassword }) {
  return (
    <form className="Form" onSubmit={onSubmit}>
      <h2>{title}</h2>
      <div className="control">
        <label htmlFor="username">Username:</label>
        <input
          value={username}
          type="text"
          onChange={(e) => setUsername(e.target.value)}
          name="username"
          id="username"
          required
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
          required
        />
      </div>
      <button type="submit">{title}</button>
    </form>
  );
}
