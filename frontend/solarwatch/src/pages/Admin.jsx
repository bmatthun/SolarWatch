import { useEffect, useState } from "react";
import Input from "../components/Input";
import Table from "../components/Table";
import { TailSpin } from "react-loader-spinner";

export default function Admin() {
    const [date, setDate] = useState('');
    const [city, setCity] = useState('');
    const [sunset, setSunset] = useState(null);
    const [sunrise, setSunrise] = useState(null);
    const [loading, setLoading] = useState(false);

    async function fetchSunsetSunrise(city, date) {
        try {
            setLoading(true);
            const res = await fetch(`/api/admin/add?city=${city.toUpperCase()}&date=${date}`, {
                method: 'POST',
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
        } finally {
            setLoading(false)
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
            {loading ? (
                <div className="loading">
                    <TailSpin color="red" radius={"8px"} />
                </div> ) : (
                    sunset && sunrise && <Table sunset={sunset} sunrise={sunrise} />)}
        </div>
    );
}
