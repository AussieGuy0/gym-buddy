import React, {useEffect, useState} from "react";
import {Api, Workout} from "../services/Api";


const Workouts: React.FC = () => {
    const [workouts, setWorkouts] = useState<Array<Workout>>([])
    const userId = 0; //TODO: Get userID from somewhere!

    useEffect(() => {
        Api.getWorkouts(userId)
            .then(() => {

            })
            .catch((err) => {
                console.warn(err)
            })

        setWorkouts([{
            date: '01/01/2020',
            title: 'Title',
            description: 'Description'
        }]);

    }, [])
    return (
        <div>
            <div className="row">
                <div className="col">
                    <h1>Workouts</h1>
                </div>
            </div>
            <div className="row">
                <div className="col">
                    <h2>All Workouts</h2>
                </div>
            </div>
            <div className="row">
                <div className="col">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Title</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>
                        {workouts.map((workout, index) => {
                            return (<tr key={index}>
                                <td>{workout.date}</td>
                                <td>{workout.title}</td>
                                <td>{workout.description}</td>
                            </tr>)
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default Workouts;
