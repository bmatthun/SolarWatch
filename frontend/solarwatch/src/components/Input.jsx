import React from "react";

export default function Input({ label, value, onChange, type = "text", id }) {
    return (
        <div className="control">
            <label htmlFor={id}>{label}:</label>
            <input
                type={type}
                value={value}
                onChange={onChange}
                name={id}
                id={id}
                required
            />
        </div>
    );
}
