import { useEffect, useState } from "react";

export default function Admin() {
    const [date, setDate] = useState('');
    const [city, setCity] = useState('');
    const [sunset, setSunset] = useState(null);
    const [sunrise, setSunrise] = useState(null);


    async function fetchSunsetSunrise(city, date) {
        try {
            const res = await fetch(`/api/admin/add?city=${city.toUpperCase()}&date=${date}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
                    'Content-Type': 'application/json'
                }
            });
            const resData = await res.json();
            console.log(resData);
            setSunset(resData.sunset); 
            setSunrise(resData.sunrise);  
        } catch (error) {
            console.error(error);
        }
    }

    function onSubmit(e) {
        e.preventDefault();
        fetchSunsetSunrise(city, date); 
        console.log(localStorage);
        
    }

    return  <div>
            {!sunset && !sunrise ? (
                
                <form className="SunsetSunrise" onSubmit={onSubmit}>
                    <div className="control">
                        <label htmlFor="city">City:</label>
                        <input
                            value={city}
                            onChange={(e) => setCity(e.target.value)}
                            name="city"
                            id="city"
                        />
                    </div>

                    <div className="control">
                        <label htmlFor="date">Date:</label>
                        <input
                            type="date"
                            value={date}
                            onChange={(e) => setDate(e.target.value)}
                            name="date"
                            id="date"
                        />
                    </div>
                    <button type="submit">Submit</button>
                </form>
            ) : (

                <>
                    <form className="SunsetSunrise" onSubmit={onSubmit}>
                        <div className="control">
                            <label htmlFor="city">City:</label>
                            <input
                                value={city}
                                onChange={(e) => setCity(e.target.value)}
                                name="city"
                                id="city"
                            />
                        </div>

                        <div className="control">
                            <label htmlFor="date">Date:</label>
                            <input
                                type="date"
                                value={date}
                                onChange={(e) => setDate(e.target.value)}
                                name="date"
                                id="date"
                            />
                        </div>
                        <button type="submit">Submit</button>
                    </form>

                    {/* Display the results in a table */}
                    <table>
                        <thead>
                            <tr>
                                <th>Sunset</th>
                                <th>Sunrise</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{sunset}</td>
                                <td>{sunrise}</td>
                            </tr>
                        </tbody>
                    </table>
                </>
            )}
        </div>
}
