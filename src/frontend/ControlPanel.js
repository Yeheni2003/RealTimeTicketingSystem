import React from "react";

function ControlPanel({ onStart, onStop, isSystemRunning }) {
    return (
        <div className="control-panel">
            {isSystemRunning ? (
                <button onClick={onStop}>Stop System</button>
            ) : (
                <button onClick={() => onStart(10, 10)}>Start System</button>
            )}
        </div>
    );
}

export default ControlPanel;
