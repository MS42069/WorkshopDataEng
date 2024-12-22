import "./banner.css";

function Banner() {
    const devEnv:string = 'prod';

  if (devEnv === 'dev') {
    return (
      <div className="infoBox">
        <div className="boxContent">
          <strong>Dev-Umgebung</strong>
        </div>
      </div>
    );
  } else {
    return <div></div>;
  }
}

export default Banner;
