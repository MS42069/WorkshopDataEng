import "./banner.css";
import { Box } from "@mui/material";

function Banner() {
  const devEnv = "prod".toString();

  if (devEnv === "dev") {
    return (
      <Box sx={{ width: "100%" }}>
        <div className="infoBox">
          <div className="boxContent">
            <strong>Dev-Umgebung</strong>
          </div>
        </div>
      </Box>
    );
  } else {
    return <div></div>;
  }
}

export default Banner;
