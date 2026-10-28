$ErrorActionPreference = 'Stop'
if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
    throw 'Docker no está instalado o no está en PATH. Instala/inicia Docker Desktop y vuelve a ejecutar.'
}
docker info *> $null
if ($LASTEXITCODE -ne 0) {
    throw 'Docker Desktop no está iniciado. Ábrelo y espera a que esté listo.'
}
if (-not (Test-Path '.env')) { Copy-Item '.env.example' '.env' }
docker compose up --build
