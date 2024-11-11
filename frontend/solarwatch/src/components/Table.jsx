import React from "react";
import sunsetImage from "../assets/sunset_graph.png";
import sunriseImage from "../assets/sunrise_graph.webp";

export default function Table({ sunset, sunrise}) {
    return (
        <table className="sunset-sunrise-table">
            <thead>
                <tr>
                    <th>Event</th>
                    <th>Image</th>
                    <th>Time</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Sunrise</td>
                    <td>
                        <img 
                            src={sunriseImage} 
                            alt="Sunrise" 
                            //className="sunset-sunrise-image"
                            style={{ width: '100px', height: 'auto' }} />
                    </td>
                    <td>{sunrise}</td>
                </tr>
                <tr>
                    <td>Sunset</td>
                    <td>
                        <img 
                            src={sunsetImage}
                            alt="Sunset" 
                            //className="sunset-sunrise-image"
                            style={{ width: '100px', height: 'auto' }} />
                    </td>
                    <td>{sunset}</td>
                </tr>
            </tbody>
        </table>
    );
}
