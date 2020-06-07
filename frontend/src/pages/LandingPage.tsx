import React from "react"
import {RewritingText} from "../components/RewritingText"

// TODO: Proper hero image!
export const LandingPage: React.FC = () => {
    return (
        <div>
            <div className="hero-container bg-light">
                <div className="container mt-5 pl-5 pr-5">
                    <div className="row">
                        <div className="col">
                            <h1 className="display-1 ta-center font-weight-bold">
                                <RewritingText template={"The best way to track $REPLACE"}
                                               replacements={["gainz.", "lifting.", "workouts.", "exercises.", "your goals.", "fitness."]}/>
                            </h1>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col">
                            <img className="img-fluid"
                                 src="https://images.unsplash.com/photo-1521805492803-3b9c3792c278?ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"
                                 alt="Barbell"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

