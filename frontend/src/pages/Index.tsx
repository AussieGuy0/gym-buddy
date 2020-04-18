import React from "react";
import {Session} from "../Session"


interface IndexProps {
    session: Session
}
const Index: React.FC<IndexProps> = ({session}) => {
    return (
        <div>
            {/*TODO: Fetch data for below*/}
            <div className="row d-flex mt-3">
                <div className="col">
                    <InfoCard title={"Last workout"} content={"8 days ago"}/>
                </div>
                <div className="col">
                    <InfoCard title={"Past 30 days"} content={"3 workouts"}/>
                </div>
                <div className="col d-none d-lg-block">
                    <InfoCard title={"Common exercise"} content={"Bicep Curl"}/>
                </div>
            </div>
            <div className="row mt-5">
                <div className="col-4">
                    <div className="card">
                        <h2 className="card-header">Info</h2>
                        <div className="card-body">
                            <div className="card-text">
                                Pretend there's some cool info here
                                <ul>
                                    <li>Item 1</li>
                                    <li>Item 2</li>
                                    <li>Item 3</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-8">
                    <span>Pretend there's a chart here</span>

                </div>
            </div>
        </div>
    );
}

interface InfoCardProps {
    title: String,
    content: String
}

const InfoCard: React.FC<InfoCardProps> = (props) => {
    return (
        <div className="card bg-info text-white h-100">
            <div className="card-body">
                <h2 className="card-title">{props.title}</h2>
                <hr/>
                <h5 className="card-text">{props.content}</h5>
            </div>
        </div>
    )
}

export default Index;
