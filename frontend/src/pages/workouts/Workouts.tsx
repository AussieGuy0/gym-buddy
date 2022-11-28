import React from 'react';
import { Api, Workout } from '../../services/Api';
import { Session } from '../../Session';
import { WorkoutForm } from './WorkoutForm';
import { AllWorkoutsTable } from './AllWorkoutsTable';
import { Card } from '../../components/cards';
import useSWR from 'swr';
import { ErrorDetails } from '../../services/Http';
import { useUser } from '../../hooks/User';

function useWorkouts(session?: Session) {
  const key = session ? '/workouts' : null;
  const { data, error, mutate } = useSWR<Workout[], ErrorDetails>(key, (key) =>
    Api.getWorkouts(session!.id)
  );
  return {
    mutate: mutate,
    workouts: data,
    isLoading: !data && !error,
    error: error,
  };
}

interface WorkoutProps {}

const Workouts: React.FC<WorkoutProps> = () => {
  const { session } = useUser();
  // TODO: use error.
  const { workouts, error, mutate } = useWorkouts(session);

  function workoutAdded(workout: Workout) {
    if (!workouts) {
      return;
    }
    const newWorkouts = [...workouts, workout];
    mutate(newWorkouts);
  }

  return (
    <div>
      <div className="row">
        <div className="col">
          <h1>Workouts</h1>
        </div>
      </div>
      <div className="row mb-3">
        <div className="col">
          <Card title="Add Workout">
            <WorkoutForm workoutAdded={workoutAdded} />
          </Card>
        </div>
      </div>
      <div className="row">
        <div className="col">
          <Card title="Past Workouts">
            {workouts != undefined && <AllWorkoutsTable workouts={workouts} />}
          </Card>
        </div>
      </div>
    </div>
  );
};

export default Workouts;
