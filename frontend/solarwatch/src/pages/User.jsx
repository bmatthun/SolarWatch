import { useEffect, useState } from "react";
import Table from "../components/Table";
import Input from "../components/Input";

export default function User() {
    const rawDate = new Date().toISOString();
    const localDate = rawDate.substring(0, rawDate.indexOf('T'));
    
    const [date, setDate] = useState(localDate);
    const [city, setCity] = useState('');
    const [sunset, setSunset] = useState(null);
    const [sunrise, setSunrise] = useState(null);

    async function fetchSunsetSunrise(city, date) {
        try {
            const res = await fetch(`/api/user/solarwatch?city=${city.toUpperCase()}&date=${date}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwt')}`,
                    'Content-Type': 'application/json'
                }
            });
            const resData = await res.json();
            setSunset(resData.sunset); 
            setSunrise(resData.sunrise);  
        } catch (error) {
            console.error(error);
        }
    }

    function onSubmit(e) {
        e.preventDefault();
        fetchSunsetSunrise(city, date); 
    }

    return (
        <div>
            <form className="SunsetSunrise" onSubmit={onSubmit}>
                <Input
                    label="City"
                    type="text"
                    value={city}
                    onChange={(e) => setCity(e.target.value)}
                    id="city"
                />
                <Input
                    label="Date"
                    type="date"
                    value={date}
                    onChange={(e) => setDate(e.target.value)}
                    id="date"
                />
                <button type="submit">Submit</button>
            </form>
            {sunset && sunrise && <Table sunset={sunset} sunrise={sunrise} />}
        </div>
    );
}
